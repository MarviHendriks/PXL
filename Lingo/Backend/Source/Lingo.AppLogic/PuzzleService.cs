using Lingo.AppLogic.Contracts;
using Lingo.Domain.Puzzle.Contracts;

namespace Lingo.AppLogic;

/// <inheritdoc cref="IPuzzleService"/>
internal class PuzzleService : IPuzzleService
{
    private IWordDictionaryRepository _wordDictionaryRepository;
    private IPuzzleFactory _puzzleFactory;
    public PuzzleService(IWordDictionaryRepository wordDictionaryRepository, IPuzzleFactory puzzleFactory)
    {
        _puzzleFactory = puzzleFactory;
        _wordDictionaryRepository = wordDictionaryRepository;
    }

    public IWordPuzzle CreateStandardWordPuzzle(int wordLength)
    {
        return _puzzleFactory.CreateStandardWordPuzzle(_wordDictionaryRepository.GetWordDictionary(wordLength));
    }
}