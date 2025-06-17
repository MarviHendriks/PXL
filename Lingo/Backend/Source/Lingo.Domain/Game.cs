using Lingo.Domain.Contracts;
using Lingo.Domain.Pit;
using Lingo.Domain.Pit.Contracts;
using Lingo.Domain.Puzzle.Contracts;

namespace Lingo.Domain
{
    /// <inheritdoc cref="IGame"/>
    internal class Game : IGame
    {
        private IList<IPuzzle> _puzzles;
        public Game(IPlayer player1, IPlayer player2, IList<IPuzzle> puzzles)
        {
            Player1 = player1;
            Player2 = player2;
            Id = Guid.NewGuid();
            PlayerToPlayId = player1.Id;
            CurrentPuzzle = puzzles[0];
            _puzzles = puzzles;
        }

        public Guid Id { get; }

        public IPlayer Player1 { get; }

        public IPlayer Player2 { get; }

        public Guid PlayerToPlayId { get; set; }

        public IPuzzle CurrentPuzzle { get; set; }

        public bool Finished
        {
            get
            {
                int count = 0;
                for (int i = 0; i < _puzzles.Count; i++)
                {
                    if (_puzzles[i].IsFinished)
                    {
                        count++;
                    }
                }
                if (count == _puzzles.Count && Player1.CanGrabBallFromBallPit == false && Player2.CanGrabBallFromBallPit == false)
                {
                    return true;
                }
                if (count != 4)
                {
                    return false;
                }

                return true;
            }
        }


        public IBall GrabBallFromBallPit(Guid playerId)
        {
            if (PlayerToPlayId != playerId)
            {
                throw new ApplicationException("beurt");
            }
            if (PlayerToPlayId == Player1.Id)
            {
                if (Player1.CanGrabBallFromBallPit == false)
                {
                    throw new ApplicationException("trekken");
                }
                IBall ball1 = Player1.GrabBallFromBallPit();
                if (Player1.CanGrabBallFromBallPit)
                {
                    if (ball1.Type == BallType.Red)
                    {
                        if (PlayerToPlayId == Player1.Id)
                        {
                            PlayerToPlayId = Player2.Id;
                        }
                        else
                        {
                            PlayerToPlayId = Player1.Id;
                        }
                    }
                }
                else
                {
                    if (ball1.Type == BallType.Red)
                    {
                        if (PlayerToPlayId == Player1.Id)
                        {
                            PlayerToPlayId = Player2.Id;
                        }
                        else
                        {
                            PlayerToPlayId = Player1.Id;
                        }
                        if (_puzzles.IndexOf(CurrentPuzzle) != _puzzles.Count -1)
                        {
                            CurrentPuzzle = _puzzles[_puzzles.IndexOf(CurrentPuzzle) + 1];
                        }

                    }
                    else
                    {
                        if (_puzzles.IndexOf(CurrentPuzzle) != _puzzles.Count - 1)
                        {
                            CurrentPuzzle = _puzzles[_puzzles.IndexOf(CurrentPuzzle) + 1];
                        }

                    }
                }
                return ball1;
            }
            else
            {
                if (Player2.CanGrabBallFromBallPit == false)
                {
                    throw new ApplicationException("trekken");
                }
                IBall ball1 = Player2.GrabBallFromBallPit();
                if (Player2.CanGrabBallFromBallPit)
                {
                    if (ball1.Type == BallType.Red)
                    {
                        if (PlayerToPlayId == Player1.Id)
                        {
                            PlayerToPlayId = Player2.Id;
                        }
                        else
                        {
                            PlayerToPlayId = Player1.Id;
                        }
                    }
                }
                else
                {
                    if (ball1.Type == BallType.Red)
                    {
                        if (PlayerToPlayId == Player1.Id)
                        {
                            PlayerToPlayId = Player2.Id;
                        }
                        else
                        {
                            PlayerToPlayId = Player1.Id;
                        }
                        if (_puzzles.IndexOf(CurrentPuzzle) != _puzzles.Count - 1)
                        {
                            CurrentPuzzle = _puzzles[_puzzles.IndexOf(CurrentPuzzle) + 1];
                        }

                    }
                    else
                    {
                        if ( _puzzles.IndexOf(CurrentPuzzle)  != _puzzles.Count - 1)
                        {
                            CurrentPuzzle = _puzzles[_puzzles.IndexOf(CurrentPuzzle) + 1];
                        }

                    }
                }
                return ball1;
            }
           
        }



        public SubmissionResult SubmitAnswer(Guid playerId, string answer)
        {
            if (playerId != PlayerToPlayId)
            {
                throw new ApplicationException("beurt");
            }
            else if (CurrentPuzzle.IsFinished)
            {
                throw new ApplicationException("beëindigd");
            }
            SubmissionResult submission = CurrentPuzzle.SubmitAnswer(answer);
           if (PlayerToPlayId == Player1.Id)
            {
                if (CurrentPuzzle.IsFinished)
                {

                    Player1.RewardForSolvingPuzzle(CurrentPuzzle);
                    if (!Player1.CanGrabBallFromBallPit)
                    {
                        if (_puzzles.IndexOf(CurrentPuzzle) != _puzzles.Count - 1)
                        {
                            CurrentPuzzle = _puzzles[_puzzles.IndexOf(CurrentPuzzle) + 1];
                        }
                    }
                    return submission;
                }
                else if (submission.LostTurn)
                {
                    if (PlayerToPlayId == Player1.Id)
                    {
                        PlayerToPlayId = Player2.Id;
                    }
                    else
                    {
                        PlayerToPlayId = Player1.Id;
                    }
                    CurrentPuzzle.RevealPart();
                    return submission;
                }
                else
                {
                    return submission;

                }
            }
           else 
            {
                if (CurrentPuzzle.IsFinished)
                {
                    Player2.RewardForSolvingPuzzle(CurrentPuzzle);
                    if (!Player2.CanGrabBallFromBallPit)
                    {
                        if (_puzzles.IndexOf(CurrentPuzzle) != _puzzles.Count - 1)
                        {
                            CurrentPuzzle = _puzzles[_puzzles.IndexOf(CurrentPuzzle) + 1];
                        }
                    }
                    return submission;
                }
                else if (submission.LostTurn)
                {
                    if (PlayerToPlayId == Player1.Id)
                    {
                        PlayerToPlayId = Player2.Id;
                    }
                    else
                    {
                        PlayerToPlayId = Player1.Id;
                    }
                    CurrentPuzzle.RevealPart();
                    return submission;
                }
                else
                {
                    return submission;

                }
            }
            
            
        }
    }
}