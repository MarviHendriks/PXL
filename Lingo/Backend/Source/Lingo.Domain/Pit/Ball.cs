using Lingo.Domain.Pit.Contracts;

namespace Lingo.Domain.Pit
{
    /// <inheritdoc cref="IBall"/>
    internal class Ball : IBall
    {
        public int Value { get;  }

        public BallType Type { get; }


        public Ball(BallType ballType, int value = 0)
        {
            Value = value;
            Type = ballType;
        }
    }

}