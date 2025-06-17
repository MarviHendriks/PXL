using Lingo.Domain.Card.Contracts;
using System.Linq;

namespace Lingo.Domain.Card;

/// <inheritdoc cref="ILingoCardFactory"/>
internal class LingoCardFactory : ILingoCardFactory
{
    private Random _rnd = new Random();
    private int num;
    public ILingoCard CreateNew(bool useEvenNumbers)
    {
        LingoCard lingo = new LingoCard(useEvenNumbers);
        for (int i = 0; i < lingo.CardNumbers.GetLength(0); i++)
        {
            for (int j = 0; j < lingo.CardNumbers.GetLength(1); j++)
            {
                num = _rnd.Next(1,70);
                if (useEvenNumbers)
                {
                    while (num % 2 != 0 || CheckIfContainsNumber(lingo, num))
                    {
                        num = _rnd.Next(1,70);
                    }
                    lingo.CardNumbers[i, j] = new CardNumber(num);
                }
                else
                {
                    while (num % 2 == 0 || CheckIfContainsNumber(lingo,  num))
                    {
                        num = _rnd.Next(1,70);
                    }
                    lingo.CardNumbers[i, j] = new CardNumber(num);
                }
            }
        }


        int count = 0;

        while (count != 8)
        {

            Random random1 = new Random();
            int number = _rnd.Next(1, 5);
            int number1 = random1.Next(1, 5);

            if (lingo.CardNumbers[number, number1].CrossedOut != true)
            {
                lingo.CardNumbers[number, number1].CrossedOut = true;
                count++;
            }


        }

        return lingo;
    }

    private bool CheckIfContainsNumber(LingoCard lingo, int number)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (lingo.CardNumbers[i,j] != null)
                {
                    if (lingo.CardNumbers[i, j].Value == number)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}