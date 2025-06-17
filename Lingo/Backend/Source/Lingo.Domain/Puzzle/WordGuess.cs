namespace Lingo.Domain.Puzzle
{
    /// <summary>
    /// A guess made in a word puzzle.
    /// Contains the guessed word and the letter matches compared to the solution
    /// </summary>
    public class WordGuess
    {
        /// <summary>
        /// The guessed word.
        /// </summary>
        /// 
        public string Word { get; set; }
        public string Solution { get; set; }
        /// <summary>
        /// Matches of the letters in the guessed <see cref="Word"/> compared to the solution.
        /// Each letter gets one of the following values:
        /// 1 (Correct): the letter matches exactly with the letter in the solution on the same position.
        /// 0 (CorrectButInWrongPosition): the letter occurs in the solution but on a different position (and is not yet matched with a letter in the solution on the correct position).
        /// -1 (DoesNotOccur): the letter does not occur on any position in the solution. 
        /// </summary>
        public LetterMatch[] LetterMatches { get; set; }

        public WordGuess(string word, string solution)
        {
            Word = word;
            Solution = solution;

            if (Word.Length != Solution.Length)
            {
                throw new ArgumentException($"De lengte {Word.Length} van het woord is niet correct");
            }
            else
            {
                LetterMatch[] _letterMatches = new LetterMatch[solution.Length];

                for (int index = 0; index < Word.Length; index++)
                {
                    if (Word[index].Equals(Solution[index]))
                    {
                        _letterMatches[index] = LetterMatch.Correct;
                        Solution = Solution.Remove(index, 1);
                        Solution = Solution.Insert(index, ".");
                        Word = Word.Remove(index, 1);
                        Word = Word.Insert(index, "*");
                    }
                    else
                    {
                        _letterMatches[index] = LetterMatch.DoesNotOccur;
                    }
                }
                for (int index = 0; index < Word.Length; index++)
                {
                    if (Solution.Contains(Word[index]))
                    {
                        _letterMatches[index] = LetterMatch.CorrectButInWrongPosition;
                        int solutionindex = Solution.IndexOf(Word[index]);
                        Solution = Solution.Remove(solutionindex, 1);
                        Solution = Solution.Insert(solutionindex, ".");
                    }
                }
                LetterMatches = _letterMatches;
            }
            Word = word;
            Solution = solution;
        }
    }
}