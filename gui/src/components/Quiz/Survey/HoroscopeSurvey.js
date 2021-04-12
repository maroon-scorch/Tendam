/*
Horoscope Survey:
1. What is your horoscope?
    * Aquarius
    * Pisces
    * Aries
    * Taurus
    * Gemini
    * Cancer
    * Leo
    * Virgo
    * Libra
    * Scorpio
    * Sagittarius
    * Capricorn
*/

const formItems = [
    {
        type: 'radio',
        label: "What is your horoscope?",
        options: ['Aquarius', 'Pisces', 'Aries', 'Taurus', 'Gemini',
        'Cancer', 'Leo', 'Virgo', 'Libra', 'Scorpio', 'Sagittarius', 'Capricorn'],
        className: 'quiz-survey-field',
        key: "horoscope",
        isRow: true
    }
];

const initialValue = {
    horoscope: ''
};

function onSubmitForm(formValue) {
    console.log(formValue);
}

export const HoroscopeJSON = {
    formItems: formItems,
    initialValue: initialValue,
    onSubmitForm: onSubmitForm
};