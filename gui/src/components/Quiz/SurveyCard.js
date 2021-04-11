import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardActionArea, CardActions, CardContent,
    CardMedia, Button, Typography } from '@material-ui/core';
import { TimeToLeaveRounded } from '@material-ui/icons';

const useStyles = makeStyles({
    root: {
        maxWidth: '70%',
        'margin-left': '15%'
    },
    media: {
        height: '20vh',
    },
    });

function SurveyCard({imageLink, title, body, ...props}) {
    const classes = useStyles();

    // Referenced from Material UI Tutorial for Card Component
    return (
        <Card className={classes.root}>
        <CardActionArea>
          <CardMedia
            className={classes.media}
            image={imageLink}
            title="survey-card-title"
          />
          <CardContent>
            <Typography gutterBottom variant="h5" component="h2">
              {title}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
                {body}
            </Typography>
          </CardContent>
        </CardActionArea>
      </Card>
    )
}

export default SurveyCard
