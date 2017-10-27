import React, {Component} from 'react';

const styles = {
    bar: {
        minHeight: 165,
    },
    title: {
        fontFamily: 'IRANSansWeb',
        color: '#fff',
        fontSize: "10pt"
    },
    content: {
        fontFamily: 'IRANSansWeb',
        color: '#999',
        fontSize: "10pt"
    }
};

export default class New extends Component {


    render() {
        return <div className="container">
            <div className="row">
                <div className="col-sm-12 col-md-6">
                    <div className="media">
                        <img className="d-flex mr-3" src="../../asserts/images/sample/sample.png"
                             alt="Generic placeholder image"/>
                        <div className="media-body">
                            <h5 className="mt-0" style={styles.title}>تیتر خبر</h5>
                            <span
                                style={styles.content}>  اینجا هم یکسر خبر باید از سمت بک اند گرفته شود و لینک به بیروم زده شود..</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    }
}