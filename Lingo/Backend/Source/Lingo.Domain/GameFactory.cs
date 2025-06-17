using Lingo.Domain.Card;
using Lingo.Domain.Card.Contracts;
using Lingo.Domain.Contracts;
using Lingo.Domain.Pit;
using Lingo.Domain.Puzzle.Contracts;

namespace Lingo.Domain
{
    internal class GameFactory : IGameFactory
    {
        private ILingoCardFactory _cardFactory;
        public GameFactory(ILingoCardFactory cardFactory)
        {
            _cardFactory = cardFactory;
        }

        public IGame CreateStandardGameForUsers(User user1, User user2, IList<IPuzzle> puzzles)
        {
            Player pl1 = new Player(user1.Id, user1.NickName, new BallPit(), _cardFactory, true);
            Player pl2 = new Player(user2.Id, user2.NickName, new BallPit(), _cardFactory, false);


            if (pl1.Name == pl2.Name)
            {
                throw new ApplicationException("beurt");
            }
            return new Game(pl1, pl2, puzzles);
        }
    }
}