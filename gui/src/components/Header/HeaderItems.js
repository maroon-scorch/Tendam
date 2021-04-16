import Button from '@material-ui/core/Button';

const headerItems = [
    {
        title: <Button variant="outlined" size="large">DASHBOARD</Button>,
        address: '/dashboard'
    },
    {
        title: <Button variant="outlined" size="large">TO-DOS</Button>,
        address: '/'
    },
    {
        title: <Button variant="outlined" size="large">SETTING</Button>,
        address: '/'
    },
    {
        title: <Button className="header-auth" variant="contained" color="secondary" size="large">LOG IN</Button>,
        address: '/login'
    }
];

export default headerItems;