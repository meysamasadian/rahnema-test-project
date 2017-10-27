import React, {Component} from 'react';
import {CircularProgress, RadioButton, RadioButtonGroup, TextField} from "material-ui";
import axios from 'axios';

const styles = {
    block: {
        maxWidth: 250,
    },
    radioButton: {
        marginBottom: 16,
    },
};

export default class Finisher extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.finish = this.finish.bind(this);
        this.state= {
            data:undefined
        }
    }

    onChange(event, value) {
        this.props.callback(value);
    }

    finish(event) {
        event.preventDefault();
        this.props.finish();
    }

    async componentDidMount() {
        let that = this;
        const {
            shop,
            product,
            purchaser,
            otp
        } = this.props;
        let url = "http://localhost:4030/api/shops/purchase/";
        let data = {"shopPan":shop,"productCode":product,"purchaserPan":purchaser,"otp":otp};
        axios.post(url,data)
            .then(function(response){
                console.log("res" ,response);
                that.setState(Object.assign(that.state,{data:response.data}));
            }).catch(function(error){
            console.log("err", error);
            that.setState(Object.assign(that.state,{data:error.response.data.view}));
            that.props.errorCallback(error.response.data.view);
        });
    }

    render() {
        const {
            data
        } = this.state;
        if (data) {
            return <div className="form-inline">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 align-items-start">
                            <p style={{margin: '20px 0', textAlign: 'center'}}>
                                {data.data}   برای خرید مجدد
                                <a
                                    href="#"
                                    onClick={this.finish}
                                >
                                    اینجا
                                </a> کلیک کنید.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        } else {
            return <div className="form-inline">
                <div className="container">
                    <div className="row center">
                        <CircularProgress size={80} thickness={5} />
                    </div>
                </div>
            </div>
        }
    }
}