using Lingo.Domain.Puzzle.Contracts;
namespace Lingo.Domain.Puzzle
{
    /// <summary>
    /// Puzzle in which letters of a word to be solved, are revealed.
    /// When all letters are revealed the puzzle is solved.
    /// Multiple guesses can be made to solve the puzzle.
    /// </summary>
    /// <inheritdoc cref="IWordPuzzle"/>
    internal class StandardWordPuzzle : IWordPuzzle
    {
        /// <summary>
        /// Constructs a word puzzle
        /// </summary>
        /// <param name="solution">The solution of the puzzle</param>
        /// <param name="wordDictionary">The dictionary of words that should be used to verify submitted answers</param>
        private string _solution;
        private HashSet<string> _wordDictionary;
        private List<WordGuess> _guesses = new List<WordGuess>();


        public StandardWordPuzzle(string solution, HashSet<string> wordDictionary)
        {
            _solution = solution;
            _wordDictionary = wordDictionary;
            Type = nameof(StandardWordPuzzle);
            Id = Guid.NewGuid();
            WordLength = _solution.Length;
            char[] ch = new char[WordLength];
            ch[0] = _solution[0];
            for (int i = 1; i < WordLength; i++)
            {
                ch[i] = '.';
            }
            RevealedLetters = ch;
            _guesses = new List<WordGuess>();
        }

        public int WordLength { get; set; }

        public IReadOnlyList<WordGuess> Guesses { get => _guesses.AsReadOnly(); }

        public char[] RevealedLetters { get; set; }

        public Guid Id { get; set; }

        public bool IsFinished { get; set; }

        public int Score
        {
            get
            {
                int count = 0;
                for (int i = 0; i < RevealedLetters.Length; i++)
                {
                    if (RevealedLetters[i] == _solution[i])
                    {
                        count++;
                    }
                }
                if (count == _solution.Length)
                {
                    return 25;
                }
                return 0;
            }
        }

        public string Type { get; set; }

        public void RevealPart()
        {
            int count = 0;
            for (int i = 0; i < RevealedLetters.Length; i++)
            {
                if (RevealedLetters[i] == Convert.ToChar("."))
                {
                    count++;
                }
            }
            if (count > 1)
            {
                for (int i = 0; i < RevealedLetters.Length; i++)
                {
                    if (RevealedLetters[i] == Convert.ToChar("."))
                    {
                        RevealedLetters[i] = _solution[i];
                        break;
                    }
                }
            }
        }

        public void RevealLetters(string word)
        {
            if (_solution.Length < word.Length)
            {
                for (int i = 0; i < _solution.Length; i++)
                {
                    if (word[i].Equals(_solution[i]))
                    {
                        RevealedLetters[i] = _solution[i];
                    }
                }
            }
            else
            {
                for (int i = 0; i < word.Length; i++)
                {
                    if (word[i] == _solution[i])
                    {
                        RevealedLetters[i] = _solution[i];
                    }
                }
            }
        }

        public void checkIsFinished(string answer)
        {
            if (answer.ToUpper() == _solution || Guesses.Count >= 6)
            {
                IsFinished = true;
            }
            else
            {
                IsFinished = false;
            }
        }

        public SubmissionResult SubmitAnswer(string answer)
        {
            string word = answer.ToUpper();
            if (word.Length != _solution.Length)
            {
                return SubmissionResult.CreateLoseTurnResult($"het antwoord heeft {word.Length} letters terwijl de oplossing er {_solution.Length} heeft");

            }
            else if (!_wordDictionary.Contains(word))
            {

                return SubmissionResult.CreateLoseTurnResult($"{word} zit niet in de woordenboek");
            }
            else if (_wordDictionary.Contains(word) && !word.Equals(_solution) && _guesses.Count == 4)
            {
                WordGuess wordGuess = new WordGuess(answer.ToUpper(), _solution);
                _guesses.Add(wordGuess);
                RevealLetters(word);
                checkIsFinished(answer);
                if (Guesses.Count == 5)
                {
                    RevealPart();
                }
                return SubmissionResult.CreateLoseTurnResult($"Maximum {_guesses.Count} pogingen");
            }
            else
            {
                WordGuess wordGuess = new WordGuess(answer.ToUpper(), _solution);
                _guesses.Add(wordGuess);
                RevealLetters(word);
                checkIsFinished(answer);
                return SubmissionResult.CreateKeepTurnResult();
            }
        }
    }
}