import React,{Component} from 'react';
import Apps from './Apps';
import News from './News';

const styles = {
    bar: {
        backgroundColor:'#000',
        opacity:.88,
        minHeight:205,
        position: 'fixed',
        bottom: 0,
        right: 0,
        left: 0
    },
    span: {
        fontFamily:'IRANSansWeb',
        color:'#fff',
        fontSize:"9pt"
    },
};

export default class LinkAndCopyRight extends Component {


    render() {
        return <div className="row">
            <div className="col-md-8 col-sm-12">

            </div>
            <div className="col-md-4 col-sm-12">
                <span style={styles.span}>تمامی حقوق این سایت متعلق به میثم اسدیان است.</span>
            </div>
        </div>
    }
}