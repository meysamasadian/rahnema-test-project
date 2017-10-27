import React,{Component} from 'react';
import Apps from './Apps';
import News from './News';

const styles = {
    root: {
        paddingTop: '5px'
    },
};

export default class NewsAndApps extends Component {


    render() {
        return <div className="row align-items-end" style={styles.root}>
            <News />
            <Apps />
        </div>
    }
}