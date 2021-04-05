import React from 'react';
import { Switch as Toggle } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import EmojiObjectsIcon from '@material-ui/icons/EmojiObjects';
import NightsStayIcon from '@material-ui/icons/NightsStay';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';

// The Toggle Button that allows the page to be switched back and forth between
// light and dark mode.

function ThemeToggle({ className, checked, checkSetter }) {
    
    const styles =  makeStyles({
        root: {
        height: '500',
        width: '200',
        padding: "200px",
        background: "red"
    },
    thumb: {
        width: 1,
        height: 2,
      },
    });

    return (
    <Typography component="div" className={className}>
        <Grid component="label" container alignItems="center" spacing={1}>
          <Grid item><EmojiObjectsIcon color={ checked ? "disabled" : "primary" } /></Grid>
          <Grid item>
            <Toggle classes={{root: styles.root, thumb: styles.thumb}}
            checked={checked}
            onChange={(e) => checkSetter(!checked)}
            />
          </Grid>
          <Grid item><NightsStayIcon color={ checked ? "secondary" : "disabled" } /></Grid>
        </Grid>
    </Typography>
    );
}

export default ThemeToggle;
