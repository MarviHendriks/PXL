using Lingo.Domain.Card.Contracts;
using Lingo.Domain.Contracts;
using Lingo.Domain.Pit;
using Lingo.Domain.Pit.Contracts;
using Lingo.Domain.Puzzle.Contracts;

namespace Lingo.Domain
{
    /// <inheritdoc cref="IPlayer"/>
    internal class Player : IPlayer
    {
        private ILingoCardFactory _cardfactory;
        private bool _useEvenNumbers;
        private Player player;
        private int count = 0;
        
        

        public Player(Guid id, string name, IBallPit ballPit, ILingoCardFactory cardFactory, bool useEvenNumbers)
        {
            Id = id;
            Name = name;
            BallPit = ballPit;
            Score = 0;
            CanGrabBallFromBallPit = false;
            _cardfactory = cardFactory;
            _useEvenNumbers = useEvenNumbers;
            Card = cardFactory.CreateNew(_useEvenNumbers);
            BallPit.FillForLingoCard(Card);
        }

        public string Name { get; set; }

        public ILingoCard Card { get; set; }

        public IBallPit BallPit { get; set; }

        public int Score { get; set; }

        public bool CanGrabBallFromBallPit { get; set; }

        public Guid Id { get; set; }

        public IBall GrabBallFromBallPit()
        {
            
            IBall grabbedBall = BallPit.GrabBall();
            
            Card.CrossOutNumber(grabbedBall.Value);
            if (grabbedBall.Type == BallType.Blue)
            {
                count++;
            }
            if (count == 2)
            {
                CanGrabBallFromBallPit = false;
                count = 0;
                
            }
            if (grabbedBall.Type == BallType.Red)
            {
                CanGrabBallFromBallPit = false;
                count = 0;
            }


            if (Card.HasLingo)
            {
                Card = _cardfactory.CreateNew(_useEvenNumbers);
                
                BallPit.FillForLingoCard(Card);
                CanGrabBallFromBallPit = false;
                Score += 100;
            }
            return grabbedBall;
        }

        public void RewardForSolvingPuzzle(IPuzzle puzzle)
        {

         
            if (!puzzle.IsFinished)
            {
                throw new InvalidOperationException();
            } if (puzzle.IsFinished && puzzle.Score != 0)
            {
                Score += 25;
                CanGrabBallFromBallPit = true;
            }         
        }
    }
}