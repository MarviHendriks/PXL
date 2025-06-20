﻿using Lingo.Domain.Card.Contracts;

namespace Lingo.Domain.Card
{
    /// <inheritdoc cref="ICardNumber"/>
    internal class CardNumber : ICardNumber
    {
        public CardNumber(int number)
        {
            Value = number;
            CrossedOut = false;
        }

        public bool CrossedOut { get; set; }

        public int Value { get; }
    }
}