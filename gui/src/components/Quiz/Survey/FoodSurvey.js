/*
Food Survey:
1. What is your favorite fruit among these options? (Banana, Apple, Pear, Grapes, Watermelon, Strawberries, Blueberries)
2. What is your least favorite fruit among this same list? (Banana, Apple, Pear, Grapes, Watermelon, Strawberries, Blueberries)
3. What do you most enjoy having for breakfast? (Cereal, Oatmeal, PB&J, Eggs, Noodles)
4. Chipotle vs Shake Shack?
5. How many meals a day do you usually eat? (1, 2, 3, 4, 5)
6. What’s your favorite meal of the day? (Breakfast, Lunch, Dinner)
7. Smoothies or Milkshakes? (Smoothies, Milkshakes)
8. How do you like your steaks? (Blue, Rare, Medium Rare, Medium, Medium Well, Well Done, Burnt, I don’t eat steak)
9. How much do you love sushi (1, 2, 3, 4, 5) with 5 being absolute favorite food
10. Avocados: yes or no? (Yes, No)
11. Blue cheese; yes or no? (Yes, No)
12. Curry: yes or no? (Yes, No)
13. Do you eat rice at least once twice a week? (Yes, No)
*/

const optionFruit = ['Banana', 'Apple', 'Pear', 'Grapes',
'Watermelon', 'Strawberries', 'Blueberries'];

const formItems = [
    {
        type: 'radio',
        label: "What is your favorite fruit among these options?",
        options: optionFruit,
        className: 'quiz-survey-field',
        key: "Q1",
        isRow: true
    },
    {
        type: 'radio',
        label: "What is your least favorite fruit among this same list?",
        options: optionFruit,
        className: 'quiz-survey-field',
        key: "Q2",
        isRow: true
    },
    {
        type: 'radio',
        label: "What do you most enjoy having for breakfast?",
        options: ['Cereal', 'Oatmeal', 'PB&J', 'Eggs', 'Noodles'],
        className: 'quiz-survey-field',
        key: "Q3",
        isRow: true
    },
    {
        type: 'radio',
        label: "Chipotle vs Shake Shack?",
        options: ['Chipotle', 'Shake Shack'],
        className: 'quiz-survey-field',
        key: "Q4",
        isRow: true
    },
    {
        type: 'radio',
        label: "How many meals a day do you usually eat?",
        options: ['1', '2', '3', '4', '5'],
        className: 'quiz-survey-field',
        key: "Q5",
        isRow: true
    },
    {
        type: 'radio',
        label: "What’s your favorite meal of the day?",
        options: ['Breakfast', 'Lunch', 'Dinner'],
        className: 'quiz-survey-field',
        key: "Q6",
        isRow: true
    },
    {
        type: 'radio',
        label: "Smoothies or Milkshakes?",
        options: ['Smoothies', 'Milkshakes'],
        className: 'quiz-survey-field',
        key: "Q7",
        isRow: true
    },
    {
        type: 'radio',
        label: "How do you like your steaks?",
        options: ['Blue', 'Rare', 'Medium Rare', 'Medium',
        'Medium Well', 'Well Done', 'Burnt', 'I don’t eat steak'],
        className: 'quiz-survey-field',
        key: "Q8",
        isRow: true
    },
    {
        type: 'radio',
        label: "How much do you love sushi with 5 being absolute favorite food.",
        options: ['1', '2', '3', '4', '5'],
        className: 'quiz-survey-field',
        key: "Q9",
        isRow: true
    },
    {
        type: 'radio',
        label: "Avocados: yes or no?",
        options: ['Yes', 'No'],
        className: 'quiz-survey-field',
        key: "Q10",
        isRow: true
    },
    {
        type: 'radio',
        label: "Blue cheese: yes or no?",
        options: ['Yes', 'No'],
        className: 'quiz-survey-field',
        key: "Q11",
        isRow: true
    },
    {
        type: 'radio',
        label: "Curry: yes or no?",
        options: ['Yes', 'No'],
        className: 'quiz-survey-field',
        key: "Q12",
        isRow: true
    },
    {
        type: 'radio',
        label: "Do you eat rice at least once twice a week?",
        options: ['Yes', 'No'],
        className: 'quiz-survey-field',
        key: "Q13",
        isRow: true
    }
];

const initialValue = {
    Q1: '',
    Q2: '',
    Q3: '',
    Q4: '',
    Q5: '',
    Q6: '',
    Q7: '',
    Q8: '',
    Q9: '',
    Q10: '',
    Q11: '',
    Q12: '',
    Q13: ''
};

function onSubmitForm(formValue) {
    console.log(formValue);
}

export const FoodJSON = {
    formItems: formItems,
    initialValue: initialValue,
    onSubmitForm: onSubmitForm
};