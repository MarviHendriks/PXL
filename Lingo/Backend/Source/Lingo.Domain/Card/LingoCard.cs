using Lingo.Domain.Card.Contracts;

namespace Lingo.Domain.Card
{
    /// <inheritdoc cref="ILingoCard"/>
    internal class LingoCard : ILingoCard
    {
        private List<int> _generatedNumbers = new List<int>();
        private Random _random = new Random();
        private LingoCard _card;

        public LingoCard(bool useEvenNumbers)
        {
            GenerateNumbers(useEvenNumbers);
            CardNumbers = new ICardNumber[5, 5];
            FillCardNumbers();
            CrossOut8RandomNumbers();
            


        }

        public ICardNumber[,] CardNumbers
        { get; }

        public bool HasLingo
        {
            get
            {
                if (VerticalLingo()|| DiagnonalLingo() || HorizontalLingo())
                {
                    return true;
                }
                return false;
            }
        }

        
        public bool VerticalLingo()
        {
            int count;
            for (int i = 0; i < 5; i++)
            {
                count = 0;
                for (int j = 0; j < 5; j++)
                {
                    
                    if (CardNumbers[j, i].CrossedOut == true)
                    {
                        count++;
                    }
                    
                }
                if (count == 5)
                {
                    return true;
                }
            }
            return false;
        }

        private bool DiagnonalLingo()
        {
            if ((CardNumbers[0, 0].CrossedOut && CardNumbers[1, 1].CrossedOut && CardNumbers[2, 2].CrossedOut && CardNumbers[3, 3].CrossedOut && CardNumbers[4, 4].CrossedOut)
                || (CardNumbers[0, 4].CrossedOut && CardNumbers[1, 3].CrossedOut && CardNumbers[2, 2].CrossedOut && CardNumbers[3, 1].CrossedOut && CardNumbers[4, 0].CrossedOut))
            {
                return true;
            }
            return false;
        }

        public bool HorizontalLingo()
        {
            int count;
            for (int i = 0; i < 5; i++)
            {
                count = 0;
                for (int j = 0; j < 5; j++)
                {

                    if (CardNumbers[i, j].CrossedOut == true)
                    {
                        count++;
                    }
                }
                if (count == 5)
                {
                    return true;
                }
            }
            return false;
        }
        public void CrossOut8RandomNumbers()
        {
            int count = 0;

            while (count != 8)
            {
                
                Random random1 = new Random();
                int number = _random.Next(1, 5);
                int number1 = random1.Next(1, 5);

                if (CardNumbers[number, number1].CrossedOut != true)
                {
                    CardNumbers[number, number1].CrossedOut = true;
                    count++;
                }
               
               
            }
            
            
        }
        public void CrossOutNumber(int number)
        {
            int k = 0;
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    if (CardNumbers[i, j].Value.Equals(number))
                    {
                        CardNumbers[i, j].CrossedOut = true;
                    }
                    
                    k++;
                }
            }
        }

        private void FillCardNumbers()
        {
            int k = 0;
                for (int i = 0; i < 5; i++)
                {
                    for (int j = 0; j < 5; j++)
                    { 
                        CardNumbers[i, j] = new CardNumber(_generatedNumbers[k]);
                    k++;
                    }
                }
        }

        private void GenerateNumbers(bool useEvenNumbers)
        {
            
            int aantal = 0;
            while (aantal < 25)
            {
                int newRandom = _random.Next(1,70);
                if (useEvenNumbers)
                {
                    while(newRandom % 2 != 0 || _generatedNumbers.Contains(newRandom))
                    {
                       newRandom = _random.Next(1,70);
                    }
                }
                else
                {
                    while (newRandom % 2 == 0 || _generatedNumbers.Contains(newRandom))
                    {
                        newRandom = _random.Next(1,70);
                    }
                }
                _generatedNumbers.Add(newRandom);
                aantal++;
            }
        }

        
    }
}
