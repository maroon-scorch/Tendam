import React from 'react';
import {
    TextField, Radio, RadioGroup,
    FormControl, FormLabel, FormControlLabel, Switch, Typography
} from '@material-ui/core';

// formItems contains the key to the initialValues
function formatForm(formItems, initialValues, setFormValue) {

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValue({
            ...initialValues,
            [name]: value
        });
    }

    const handleSwitch = (e) => {
        const { name, checked } = e.target;
        setFormValue({
            ...initialValues,
            [name]: checked
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
                defaultValue={initialValues[props.key]}
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
                defaultValue={initialValues[props.key]}
                onChange={handleChange}
                key={index}
            />
            </div>
        );
    };

    /*
    Accepts a JSON of form:
        {
        type: 'radio',
        label: The question of the entry,
        options: The options for to select from,
        className: className,
        key: the name of the entry, which should be the same as the key to initialValues
        isRow: true => display radio in rows; false => display radio in columns
        }
    */
    function toRadioSelection(props, index) {
        return (
            <div>
                <FormControl>
                <FormLabel>{(index + 1) + ". " + props.label}</FormLabel>
                <RadioGroup row={props.isRow} name={props.key} value={initialValues[props.key]} onChange={handleChange}>
                    {
                        props.options.map((item, index) => {
                            return(<FormControlLabel key={item + index} value={item} control={<Radio />} label={item} />);
                        })
                    }
                </RadioGroup>
                </FormControl>
            </div>
        );
    };

    function toSwitch(props, index) {
        return (
            <div>
                <FormLabel>
                    {props.label}
                </FormLabel>
                <Switch
                    variant="outlined"
                    className={props.className}
                    name={props.key}
                    checked={initialValues[props.key]}
                    onChange={handleSwitch}
                    key={index}
                />
            </div>
        );
    };

    function toText(props, index) {
        return (
            <div>
                <Typography
                    className={props.className}
                    variant={props.textType}
                    key={index}>
                    {props.label}
                </Typography>
            </div>
        );
    };

    const convertDict = {
        'text': toTextBox,
        'password': toEncryptedBox,
        'radio': toRadioSelection,
        'switch': toSwitch,
        'label': toText,
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