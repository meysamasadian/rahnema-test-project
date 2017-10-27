import React, {Component} from 'react';
import New from "./New";

const styles = {
    bar: {
        minHeight: 165,
    },
    title: {
        fontFamily: 'IRANSansWeb',
        color: '#fff',
        fontSize: "11pt"
    }
};

export default class News extends Component {


    render() {
        return <div className="col-md-8 col-sm-12" style={styles.bar}>
            <div className="container">
                <div className="row">
                    <div className="col">
                        <h5 style={styles.title}>آخرین اخبار</h5>
                    </div>
                </div>
                <div className="row">
                    <New/>
                </div>
                <br/>
                <div className="row">
                    <New/>
                </div>
            </div>
        </div>
    }
}