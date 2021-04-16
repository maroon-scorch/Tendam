import React from 'react';
import './App.css';

// Routing Mechanics, using React Router
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import PrivateRoute from '../service/PrivateRoute.js';
import PublicRoute from '../service/PublicRoute.js';

// Style Related Components
import { ThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

// Base Components Without Login
import Header from '../components/Header/Header.js';
import Home from '../components/Home/Home.js';

// Authentication Related Components
import { AuthProvider } from "../context/AuthContext.js";
import Signup from '../components/Authentication/Signup/Signup.js';
import Login from '../components/Authentication/Login/Login.js';
import ForgotPassword from '../components/Authentication/ForgotPassword/ForgotPassword.js';
import TermsOfService from '../components/Authentication/TermsOfService/TermsOfService.js';

// Application Components Accessible After Authenticated
import DashBoard from '../components/Dashboard/Dashboard.js';
import Quiz from '../components/Quiz/Quiz.js';
import QuizSurvey from '../components/Quiz/Survey/QuizSurvey.js';
import BlackJack from '../components/Game/BlackJack.js';
import Setting from '../components/Setting/Setting.js';
import Match from '../components/Match/Match.js';
import Profile from '../components/Profile/Profile.js';
import OtherProfile from '../components/Profile/OtherProfile.js';
import Chat from "../components/Chat/Chat";

// Context for access to Database and invokation to Modals
import { DatabaseProvider } from "../context/DatabaseContext.js";
import { ModalProvider } from '../context/ModalContext.js';

// The Core App of the Page
function App() {
  // The Theme of the Application
  const darkMode = createMuiTheme({
    palette: {
      type: "dark",
      primary: {
        main: '#301e5d'
        },
      secondary: {
        main: '#9a0036'
      }
    }
  });

  // The Header stays consistent during transition.
  // The Body is surrounded by the Provider (useContext) for Authentication, Database, and Modal.
  return (
    <ThemeProvider theme={darkMode}>
      <CssBaseline />
      <div className="App">
      <Router>
        <ModalProvider>
          <AuthProvider>
            <DatabaseProvider>
            <Header />
            <Switch>
              <Route path='/' exact component={Home}></Route>
              <PublicRoute path='/signup' exact component={Signup}></PublicRoute>
              <Route path='/login' exact component={Login}></Route>
              <Route path='/forgot-password' exact component={ForgotPassword}></Route>
              <Route path="/terms-of-service" exact component={TermsOfService}></Route>
              <PrivateRoute path="/dashboard" exact component={DashBoard}></PrivateRoute>
              <PrivateRoute path="/games" exact component={BlackJack}></PrivateRoute>
              <PrivateRoute path="/quizzes" exact component={Quiz}></PrivateRoute> 
              <PrivateRoute path="/quizzes/:name" component={QuizSurvey}></PrivateRoute> 
              <PrivateRoute path="/setting" exact component={Setting}></PrivateRoute>
              <PrivateRoute path="/match" exact component={Match}></PrivateRoute>
              <PrivateRoute path="/profile" exact component={Profile}/>
              <PrivateRoute path="/profile/:name"
              component={props => <OtherProfile key={props.location.key} {...props} />}></PrivateRoute>
              <PrivateRoute path="/message" component={Chat}></PrivateRoute>
              <PrivateRoute path="/message#:name" component={Chat}></PrivateRoute>
            </Switch>
            </DatabaseProvider>
          </AuthProvider>
        </ModalProvider>
      </Router>
      </div>
    </ThemeProvider>
  );
}

export default App;
