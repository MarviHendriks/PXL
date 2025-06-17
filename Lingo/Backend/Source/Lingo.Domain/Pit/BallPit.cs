using Lingo.Domain.Card.Contracts;
using Lingo.Domain.Pit.Contracts;

namespace Lingo.Domain.Pit
{
    /// <inheritdoc cref="IBallPit"/>
    internal class BallPit : IBallPit
    {
        private IList<IBall> _ballList;
        public BallPit()
        {
            _ballList = new List<IBall>();
        }

        public void FillForLingoCard(ILingoCard lingoCard)
        {
            _ballList = new List<IBall>();
            for (int i = 0; i < 3; i++)
            {
                IBall ball = new Ball(BallType.Red);
                _ballList.Add(ball);
            }
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    if (lingoCard.CardNumbers[i, j].CrossedOut == false)
                    {
                        IBall ball = new Ball(BallType.Blue, lingoCard.CardNumbers[i, j].Value);
                        _ballList.Add(ball);
                    }
                }
            }
        }

        public IBall GrabBall()
        {
            Random rand = new Random();
            int randomBall = _ballList.Count - 1;
            IBall ball = _ballList[rand.Next(randomBall)];
            if (ball.Type != BallType.Red)
            {
                _ballList.Remove(ball);
            }
            return ball;
        }
    }
}