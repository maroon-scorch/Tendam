import React, {useState, useEffect} from 'react'

import { Button } from '@material-ui/core';
import formatForm from '../../../service/Form/createForm.js';

import Aos from 'aos';
import "aos/dist/aos.css";

import './QuizSurvey.css';

import { FoodJSON } from './FoodSurvey';
import { HoroscopeJSON } from './HoroscopeSurvey';
import { PersonalityJSON } from './PersonalitySurvey';

import { useAuth } from "../../../context/AuthContext";
import { useDatabase } from "../../../context/DatabaseContext";
import { useHistory } from 'react-router-dom';

const formDict = {
    'food': FoodJSON,
    'horoscope': HoroscopeJSON,
    'mbti': PersonalityJSON
}

function QuizSurvey({ match }) {
    const { currentUser } = useAuth();
    const { writeSurvey } = useDatabase();
    const history = useHistory();

    const [formValues, setFormValues] = useState({});
    const [formItems, setFormItems] = useState([]);

    function submitForm() {
        console.log(formValues);
    }

    function validate() {
        if (formValues === {}) {
            alert('Please select the form from Quizzes instead.');
            return false;
        }

        for (let key in formValues) {
            if (formValues[key] === '') {
                alert('Please complete the form!');
                return false;
            }    
        }
        return true;
    }

    function uploadResponse() {
        let formValue = formValues;
        if (validate()) {
            let base = [];
        
            for (let ind in formItems) {
                let currentEntry = formItems[ind];
                let quest = currentEntry.label;
                let ans = formValue[currentEntry.key];

                base.push({
                    key: currentEntry.key,
                    question: quest,
                    answer: ans
                });
            }
            
            console.log(base);
            console.log(currentUser.uid);
            let field = match.params.name;
            writeSurvey(currentUser.uid, field, base).then(() => {
                console.log("Document successfully written!");
                history.push('/dashboard');
            })
            .catch((error) => {
                console.error("Error writing document: ", error);
            });
        }
    }

    useEffect(() => {
        Aos.init({});
        let targetJSON = formDict[match.params.name];
        console.log(targetJSON);

        if (typeof targetJSON !== 'undefined') {
            setFormValues(targetJSON['initialValue']);
            setFormItems(targetJSON['formItems']);
        }

        // console.log(formValues);
        // console.log(formItems);
        // console.log(onSubmitForm);
    }, []);

    return (
        <div className="quiz-survey-container">
            <div className="quiz-survey-form" data-aos="fade-up" data-aos-duration="2250">
                {match.params.name}
                <form autoComplete="off">
                    {formatForm(formItems, formValues, setFormValues)}
                    {/* <Button id="survey-submit" variant="contained" color="secondary"
                    onClick={formDict[match.params.name] ? () => { formDict[match.params.name]['onSubmitForm'](formValues) } : submitForm}> */}
                    <Button id="survey-submit" variant="contained" color="secondary" onClick={uploadResponse}>
                    Submit
                    </Button>
                </form>
            </div>
        </div>
    )
}

export default QuizSurvey
