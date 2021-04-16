/*
MBTI Survey: (On a scale of 1-5, with 1 being completely disagree and 5 being completely agree)
1. I quickly lose energy when I spend too much time around others (I)
2. You don’t mind spending lots of time in your own company (I)
3. You often come up with backup plans (J)
4. You active seek leadership roles when in a group (J)
5. You never put things off to the last minute (J)
6. You enjoy maintaining and organizing a busy calendar (J)
7. You enjoy politics and philosophy (N)
8. You enjoy works of art that leave a lot up to interpretation (N)
9. You often find yourself lost in thought about the future (N)
10. You get impatient with inefficient group members (T)
11. You’re rarely affectionate (F)
12. It’s more important to follow your heart than to do what is most efficient (F)
13. You have an easy time empathizing with people who are very different from you (F)
14. You’re would rather be admired than adored (T)
15. You always prepare rather than improvise (T)
16. The ends justify the means (T)
*/

const option15 = ['1', '2', '3', '4', '5'];

const formItems = [
    {
        type: 'radio',
        label: "I quickly lose energy when I spend too much time around others.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q1",
        isRow: true,
        personality: 'I'
    },
    {
        type: 'radio',
        label: "You don’t mind spending lots of time in your own company.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q2",
        isRow: true,
        personality: 'I'
    },
    {
        type: 'radio',
        label: "You often come up with backup plans.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q3",
        isRow: true,
        personality: 'J'
    },
    {
        type: 'radio',
        label: "You active seek leadership roles when in a group.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q4",
        isRow: true,
        personality: 'J'
    },
    {
        type: 'radio',
        label: "You never put things off to the last minute.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q5",
        isRow: true,
        personality: 'J'
    },
    {
        type: 'radio',
        label: "You enjoy maintaining and organizing a busy calendar.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q6",
        isRow: true,
        personality: 'J'
    },
    {
        type: 'radio',
        label: "You enjoy politics and philosophy.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q7",
        isRow: true,
        personality: 'N'
    },
    {
        type: 'radio',
        label: "You enjoy works of art that leave a lot up to interpretation.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q8",
        isRow: true,
        personality: 'N'
    },
    {
        type: 'radio',
        label: "You often find yourself lost in thought about the future.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q9",
        isRow: true,
        personality: 'N'
    },
    {
        type: 'radio',
        label: "You get impatient with inefficient group members.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q10",
        isRow: true,
        personality: 'T'
    },
    {
        type: 'radio',
        label: "You’re rarely affectionate.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q11",
        isRow: true,
        personality: 'F'
    },
    {
        type: 'radio',
        label: "It’s more important to follow your heart than to do what is most efficient.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q12",
        isRow: true,
        personality: 'F'
    },
    {
        type: 'radio',
        label: "You have an easy time empathizing with people who are very different from you.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q13",
        isRow: true,
        personality: 'F'
    },
    {
        type: 'radio',
        label: "You’re would rather be admired than adored.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q14",
        isRow: true,
        personality: 'T'
    },
    {
        type: 'radio',
        label: "You always prepare rather than improvise.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q15",
        isRow: true,
        personality: 'T'
    },
    {
        type: 'radio',
        label: "The ends justify the means.",
        options: option15,
        className: 'quiz-survey-field',
        key: "Q16",
        isRow: true,
        personality: 'T'
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
    Q13: '',
    Q14: '',
    Q15: '',
    Q16: ''
};

function onSubmitForm(formValue) {
    console.log(formValue);
    // let base = {};

    // for (let ind in formItems) {
    //     let currentEntry = formItems[ind];
    //     let quest = currentEntry.label;
    //     let ans = formValue[currentEntry.key];
    //     base[currentEntry.key] = {
    //         question: quest,
    //         answer: ans
    //     };
    // }

    // console.log(base);
    // writeSurvey('iLgQ7fahR8ZIv43GOjxVAy7Xl6o2', 'mtbi', base);

}

export const PersonalityJSON = {
    formItems: formItems,
    initialValue: initialValue,
    onSubmitForm: onSubmitForm
};