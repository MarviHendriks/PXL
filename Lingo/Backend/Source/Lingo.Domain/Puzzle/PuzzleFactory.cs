using Lingo.Domain.Puzzle.Contracts;

namespace Lingo.Domain.Puzzle
{
    /// <inheritdoc cref="IPuzzleFactory"/>
    internal class PuzzleFactory : IPuzzleFactory
    {
        private StandardWordPuzzle _wordPuzzle;
        private string _solution;
        private HashSet<string> _wordDictionary;
        private static Random _random = new Random();

        public IWordPuzzle CreateStandardWordPuzzle(HashSet<string> wordDictionary)
        {
            _wordDictionary = wordDictionary;
            _solution = wordDictionary.ElementAt(_random.Next(_wordDictionary.Count()));
            _wordPuzzle = new StandardWordPuzzle(_solution, _wordDictionary);
            return _wordPuzzle;
        }
    }
}