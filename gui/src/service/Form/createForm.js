import React from 'react';
import { TextField } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';

// formItems contains the key to the initialValues
function formatForm(formItems, initialValues, setFormValue) {

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValue({
            ...initialValues,
            [name]: value
        });
    }

    function toTextBox(props, index) {
        return (
            <div>
            <TextField
                variant="outlined"
                className={props.className}
                name={props.key}
                label={props.label}
                value={initialValues[props.key]}
                onChange={handleChange}
                key={index}
            />
            </div>
        );
    };

    function toEncryptedBox(props, index) {
        return (
            <div>
            <TextField
                variant="outlined"
                type={"password"}
                className={props.className}
                name={props.key}
                label={props.label}
                value={initialValues[props.key]}
                onChange={handleChange}
                key={index}
            />
            </div>
        );
    };

    const convertDict = {
        'text': toTextBox,
        'password': toEncryptedBox
    };

    return (
        formItems.map((item, index) => {
            let type = item['type'];
            let result = convertDict[type](item, index);
            return(result);
        })
    )
}

export default formatForm;