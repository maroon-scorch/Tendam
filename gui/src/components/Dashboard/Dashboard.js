import React, { useState, useRef } from 'react'
import { useAuth } from "../../context/AuthContext.js";
import { Link, useHistory } from "react-router-dom";

import circus from "./selection/circus-transparent.png";
import book from "./selection/book-transparent.png";
import wheel from "./selection/setting-transparent.png";
import profile from "./selection/profile-transparent.png";
import match from "./selection/match-transparent.png";

import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import Slider from "react-slick";

import "./Dashboard.css";

import { Typography } from '@material-ui/core';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

// Referenced and Adapted from https://github.com/chrisdesilva/3d-slider

function Dashboard() {
    const history = useHistory();
    const { currentUser } = useAuth();
    const [currentIndex, setIndex] = useState(0);
    const isFirstLoad = useRef(true);

    // let settings = {
    //     width: '50%',
    //     infiniteLoop: true
    // };

    const Next = ({ onClick }) => {
        return (
          <div className="dashboard-next" onClick={onClick}>
            →
          </div>
        );
    };
    
    const Prev = ({ onClick }) => {
        return (
          <div className="dashboard-prev" onClick={onClick}>
            ←
          </div>
        );
    };

    function handleChange(current) {
        console.log(current);
        setIndex(current);
    }

    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        swipeToSlide: true,
        centerMode: true,
        centerPadding: 0,
        afterChange: handleChange,
        adaptiveHeight: true,
        initialSlide: currentIndex
    };

    const sliderItems = [
        {
            children: <Link to='/games'>
                <img src={circus} width={'300px'} height={'300px'} className="unselected-item" />
            </Link>
        },
        {
            children: <Link to='/quizzes'>
                <img src={book} width={'300px'} height={'300px'} className="unselected-item" />
                </Link>
        },
        {
            children: <Link to='/setting'>
                <img src={wheel} width={'300px'} height={'300px'} className="unselected-item" /></Link>
        },
        {
            children: <Link to='/profile'>
                <img src={profile} width={'300px'} height={'300px'} className="unselected-item"  /></Link>
        },
        {
            children: <Link to='/match'>
                <img src={match} width={'300px'} height={'300px'} className="unselected-item" /></Link>
        }
    ];

    return (
        <div className="dashboard-container">
            <br />
            <Typography variant="h1" className="signup-title">Dashboard</Typography>
        <Slider {...settings}>
            {sliderItems.map((item, index) => {
                return(<div>
                    <div className={ index===currentIndex ? "selected-item" : "unselected-item"}>
                    {item.children}
                    </div>
                </div>);
            })}
      </Slider>
      {/* <div className="background-container">
                <img src={neon} />
            </div> */}
      </div>
        // <div className="dashboard-container">
        //     <Typography variant="h6" className="signup-title">
        //         {currentUser.email}</Typography>
        //         {/* <Carousel {...settings}>
        //         <div>
        //         <img src={circus} />
        //             <p className="legend">Games</p>
        //         </div>
        //         <div>
        //         <img src={book} />
        //             <p className="legend">Quizzes</p>
        //         </div>
        //         <div>
        //         <img src={wheel} />
        //             <p className="legend">Settings</p>
        //         </div>
        //     </Carousel>        </div> */}
    )
}

export default Dashboard
