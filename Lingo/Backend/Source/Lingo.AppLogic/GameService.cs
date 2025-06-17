using Lingo.AppLogic.Contracts;
using Lingo.Domain;
using Lingo.Domain.Contracts;
using Lingo.Domain.Pit.Contracts;
using Lingo.Domain.Puzzle.Contracts;

namespace Lingo.AppLogic
{
    /// <inheritdoc cref="IGameService"/>
    internal class GameService : IGameService
    {
        private IGameRepository _gameRepository;
        private IGameFactory _gameFactory;
        private IPuzzleService _puzzleService;
        private string _answer;

        public GameService(IGameRepository gameRepository, 
            IGameFactory gameFactory, 
            IPuzzleService puzzleService, 
            IUserRepository userRepository)
        {
            _gameRepository = gameRepository;
            _gameFactory = gameFactory;
            _puzzleService = puzzleService;
        }

        public void CreateGameForUsers(User user1, User user2, GameSettings settings)
        {
            IList<IPuzzle> puzzles = new List<IPuzzle>();
            for (int i = 0; i < settings.NumberOfStandardWordPuzzles; i++)
            {
                puzzles.Add(_puzzleService.CreateStandardWordPuzzle(settings.MaximumWordLength));
            }
            IGame game = _gameFactory.CreateStandardGameForUsers(user1, user2, puzzles);
            _gameRepository.Add(game);

        }

        public IList<IGame> GetGamesFor(Guid userId)
        {
           return _gameRepository.GetGamesOfUser(userId);
        }

        public IGame GetById(Guid gameId)
        {
            return _gameRepository.GetById(gameId);
        }

        public SubmissionResult SubmitAnswer(Guid gameId, Guid playerId, string answer)
        {
            return GetById(gameId).SubmitAnswer(playerId, answer);
        }

        public IBall GrabBallFromBallPit(Guid gameId, Guid playerId)
        {

            return GetById(gameId).GrabBallFromBallPit(playerId);
        }
    }
}
