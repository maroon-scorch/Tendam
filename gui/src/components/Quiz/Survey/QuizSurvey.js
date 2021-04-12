import React, {useState, useEffect} from 'react'

import { Button } from '@material-ui/core';
import formatForm from '../../../service/Form/createForm.js';

import Aos from 'aos';
import "aos/dist/aos.css";

import './QuizSurvey.css';

import { FoodJSON } from './FoodSurvey';
import { HoroscopeJSON } from './HoroscopeSurvey';
import { PersonalityJSON } from './PersonalitySurvey';

const formDict = {
    'food': FoodJSON,
    'horoscope': HoroscopeJSON,
    'mbti': PersonalityJSON
}

function QuizSurvey({ match }) {

    const [formValues, setFormValues] = useState({});
    const [formItems, setFormItems] = useState([]);

    function submitForm() {
        console.log(formValues);
    }

    const [onSubmitForm, setSubmitFunction] = useState(submitForm);

    useEffect(() => {
        Aos.init({});
        let targetJSON = formDict[match.params.name];
        console.log(targetJSON);

        if (typeof targetJSON !== 'undefined') {
            setFormValues(targetJSON['initialValue']);
            setFormItems(targetJSON['formItems']);
            // setSubmitFunction(targetJSON['onSubmitForm']);
        }

        console.log(formValues);
        console.log(formItems);
        console.log(onSubmitForm);
    }, []);

    return (
        <div className="quiz-survey-container">
            <div className="quiz-survey-form" data-aos="fade-up" data-aos-duration="2250">
                {match.params.name}
                <form autoComplete="off">
                    {formatForm(formItems, formValues, setFormValues)}
                    <Button id="survey-submit" variant="contained" color="secondary"
                    onClick={formDict[match.params.name] ? () => { formDict[match.params.name]['onSubmitForm'](formValues) } : submitForm}>
                    Submit
                    </Button>
                </form>
            </div>
        </div>
    )
}

export default QuizSurvey
