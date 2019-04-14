package fr.jonathan.mockdata

import fr.jonathangerbaud.core.ext.d
import java.util.*

object MockData
{
    data class SimplePersonItem(val firstName:String, val lastName:String, val avatarImageUrl:String)

    fun simplePersonItem(generator: MockGenerator): SimplePersonItem
    {
        return SimplePersonItem(
            generator.name().firstName(),
            generator.name().lastName(),
            generator.avatar().image()
        )
    }

    data class Person(val title:String, val firstName:String, val lastName:String, val avatarImageUrl:String, val birthday: Date, val address: Address, val sex:String, val nationality:String)

    fun person(generator: MockGenerator): Person
    {
        return Person(
            generator.name().title(),
            generator.name().firstName(),
            generator.name().lastName(),
            generator.avatar().image(),
            generator.date().birthday(),
            address(generator),
            generator.demographic().sex(),
            generator.demographic().demonym()
        )
    }

    data class ContactInfo(val title:String, val firstName:String, val lastName:String, val avatarImageUrl:String, val phoneNumber: String, val cellNumber:String, val email:String, val company:String)

    fun contact(generator: MockGenerator): ContactInfo
    {
        return ContactInfo(
            generator.name().title(),
            generator.name().firstName(),
            generator.name().lastName(),
            generator.avatar().image(),
            generator.phoneNumber().phoneNumber(),
            generator.phoneNumber().cellPhone(),
            generator.internet().emailAddress(),
            generator.company().name()
        )
    }

    class Employee (val firstName: String, val lastName: String, val avatarImageUrl: String, val jobTitle:String, val position:String, val company: Company)

    fun employee(generator: MockGenerator): Employee
    {
        return Employee(
            generator.name().firstName(),
            generator.name().lastName(),
            generator.avatar().image(),
            generator.company().profession(),
            generator.job().title(),
            company(generator)
        )
    }

    data class Address(val streetName:String, val streetNumber:String, val streetAddress:String, val streetAddressTwoLines:String, val zipCode:String, val city:String, val state:String, val stateAbbr:String)

    fun address(generator: MockGenerator): Address
    {
        return Address(
            generator.address().streetName(),
            generator.address().streetAddressNumber(),
            generator.address().streetAddress(),
            generator.address().streetAddress(true),
            generator.address().zipCode(),
            generator.address().city(),
            generator.address().state(),
            generator.address().stateAbbr()
        )
    }

    data class Company(val name:String, val address: Address, val url:String, val industry:String, val catchPhrase:String, val bs:String)

    fun company(generator: MockGenerator): Company
    {
        return Company(
            generator.company().name(),
            address(generator),
            generator.company().url(),
            generator.company().industry(),
            generator.company().catchPhrase(),
            generator.company().bs()

        )
    }
}