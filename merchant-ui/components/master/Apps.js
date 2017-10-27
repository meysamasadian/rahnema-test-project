import React,{Component} from 'react';
import FlatButton from 'material-ui/FlatButton';
import FontIcon from 'material-ui/FontIcon';
import Divider from 'material-ui/Divider';

const styles = {
    bar: {
        minHeight:165,
    },
    links: {
        marginTop:15,
    },
    title: {
        fontFamily:'IRANSansWeb',
        color:'#fff',
        fontSize:"11pt"
    }
};

export default class News extends Component {


    render() {
        return <div className="col-md-4 col-sm-12" style={styles.bar}>
            <div className="container">
                <div className="row">
                    <div className="col">
                        <h5 style={styles.title}>دانلود نرم افزارهای کارگاه</h5>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3">
                        <FlatButton
                            primary={true}
                            icon={<FontIcon className="fa fa-android" />} />
                    </div>
                    <div className="col-md-3">
                        <FlatButton
                            primary={true}
                            icon={<FontIcon className="fa fa-apple" />} />
                    </div>
                </div>
                <Divider />
                <div className="row align-items-end">
                    <div className="col" style={styles.links}>
                        <h5 style={styles.title}>لینک های مفید</h5>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-3 col-sm-2">
                        <FlatButton
                            primary={true}
                            icon={<FontIcon className="fa fa-home" />} />
                    </div>
                    <div className="col-md-3 col-sm-2">
                        <FlatButton
                            primary={true}
                            icon={<FontIcon className="fa fa-linkedin" />} />
                    </div>
                    <div className="col-md-3 col-sm-2">
                        <FlatButton
                            primary={true}
                            icon={<FontIcon className="fa fa-telegram" />} />
                    </div>
                    <div className="col-md-3 col-sm-2">
                        <FlatButton
                            primary={true}
                            icon={<FontIcon className="fa fa-instagram" />} />
                    </div>
                </div>
            </div>
        </div>
    }
}