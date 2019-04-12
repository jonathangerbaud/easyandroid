package fr.jonathan.mockdata

import com.github.javafaker.Faker
import java.util.Locale


class MockGenerator(val locale:Locale?) : Faker(locale, null)
{
    constructor() : this(Locale.getDefault())
}