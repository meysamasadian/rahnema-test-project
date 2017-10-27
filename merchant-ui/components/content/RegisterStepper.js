import React from 'react';
import {
    Step,
    Stepper,
    StepLabel,
} from 'material-ui/Stepper';
import RaisedButton from 'material-ui/RaisedButton';
import FlatButton from 'material-ui/FlatButton';
import BasicStep from "./BasicStep";
import VerificationStep from "./VerificationStep";
import PasswordStep from "./PasswordStep";
import PropTypes from 'prop-types';
import {Snackbar, StepContent} from "material-ui";
import UserTypeStep from "./UserTypeStep";
import Finisher from "./Finisher";

export default class RegisterStepper extends React.Component {

    state = {
        finished: false,
        stepIndex: 0,
        shop:"",
        product:"",
        purchaser:"",
        otp:"",
        cont:true,
        error:"",
    };

    handleNext = () => {
        const {stepIndex} = this.state;
        let cont = true;
        let error = "";
        switch (stepIndex) {
            case 0:
                if (this.state.shop === "") {
                    cont = false;
                    error = "باید یک فروشگاه انتخاب شود";
                }
                break;
            case 1:
                if (this.state.product === "") {
                    cont = false;
                    error = "باید یک محصول انتخاب شود";
                }
                break;
            case 2:
                if (this.state.purchaser === "") {
                    cont = false;
                    error = "باید شماره تلفن وارد شود";
                }
                break;
            case 3:
                if (this.state.otp === "") {
                    cont = false;
                    error = "باید یکبار رمز وارد شود";
                }
                break;
            default:
                return '';
        }
        if (cont) {
            this.setState(Object.assign(this.state,{
                stepIndex: stepIndex + 1,
                finished: stepIndex >= 3,
                cont: true,
                error: "",
            }));
        } else {
            this.setState(Object.assign(this.state,{
                cont: false,
                error: error,
            }));
        }
    };

    handlePrev = () => {
        const {stepIndex} = this.state;
        if (stepIndex > 0) {
            this.setState(Object.assign(this.state,{purchaser:"",stepIndex: stepIndex - 1}));
        }
    };

    setShop = (shop) => {
        console.log("set shop", shop);
        this.setState(Object.assign(this.state, {shop:shop}));
    };

    setProduct = (product) => {
        this.setState(Object.assign(this.state, {product:product}));
    };

    setPurchaser = (purchaser) => {
        this.setState(Object.assign(this.state, {purchaser:purchaser}));
    };

    setOtp = (otp) => {
        this.setState(Object.assign(this.state, {otp:otp}));
    };


    errorCallback = (error) => {
        let index = 2;
        console.log("dhdgfdshfdfshd=??????  " , error);
        this.setState(Object.assign(this.state, {cont:false, purchaser:"", error:error, stepIndex:index, finished:false}));
    };

    getStepContent(stepIndex) {
        switch (stepIndex) {
            case 0:
                return 'فروشگاه مورد نظر را انتخاب کنید';
            case 1:
                return 'محصول مورد نظر را انتخاب کنید';
            case 2:
                return 'شماره تلفن همراه خود را وارد کنید';
            case 3:
                return 'کد شش رقمی مقابل را ارسال نمایید';
            default:
                return '';
        }
    }


    getStepForm(stepIndex) {
        console.log("dsdsfdsfdfsdsdf", stepIndex);
        switch (stepIndex) {
            case 0:
                return <BasicStep shop={this.state.shop} callback={this.setShop}/>;
            case 1:
                return <VerificationStep shop={this.state.shop} product={this.state.product} callback={this.setProduct} />;
            case 2:
                return <PasswordStep shop={this.state.shop} product={this.state.product}  callback={this.setPurchaser} />;
            case 3:
                return <UserTypeStep shop={this.state.shop} product={this.state.product} purchaser={this.state.purchaser}
                                     callback={this.setOtp} errorCallback={this.errorCallback} />;
            default:
                return '';
        }
    }

    finish =()=> {
        console.log("inja omadiam");
        this.setState(Object.assign(this.state, {
            shop:"",
            product:"",
            purchaser:"",
            otp:"",
            cont:true,
            error:"",
            stepIndex: 0,
            finished: false
        }));
    }


    handleRequestClose = () => {
        this.setState({
            cont: true,
        });
    };

    renderStepActions(step) {
        const {stepIndex} = this.state;

        return (
            <div style={{margin: '12px 0'}}>
                <RaisedButton
                    label={stepIndex === 3 ? 'خرید' : 'مرحله بعدی'}
                    disableTouchRipple={true}
                    disableFocusRipple={true}
                    primary={true}
                    onClick={this.handleNext}
                    style={{marginRight: 12}}
                />
                {step > 0 && (
                    <FlatButton
                        label="بازگشت"
                        disabled={stepIndex === 0}
                        disableTouchRipple={true}
                        disableFocusRipple={true}
                        onClick={this.handlePrev}
                    />
                )}
            </div>
        );
    }


    render() {
        const {finished, stepIndex, cont, error} = this.state;
        const contentStyle = {margin: '0 16px'};
            return (
                <div style={{width: '100%', maxWidth: 700, minWidth: 500, margin: 'auto'}}>
                    <Stepper activeStep={stepIndex}>
                        <Step>
                            <StepLabel>انتخاب فروشگاه</StepLabel>
                        </Step>
                        <Step>
                            <StepLabel>انتخاب محصول</StepLabel>
                        </Step>
                        <Step>
                            <StepLabel>ورود به سیستم</StepLabel>
                        </Step>
                        <Step>
                            <StepLabel>اعتبارسنجی</StepLabel>
                        </Step>
                    </Stepper>
                    <div style={contentStyle}>
                        {finished ? (
                            <Finisher finish={this.finish}
                                      shop={this.state.shop}
                                      product={this.state.product}
                                      purchaser={this.state.purchaser}
                                      otp={this.state.otp}
                                      errorCallback={this.errorCallback} />
                        ) : (
                            <div>
                                <p className="">{this.getStepContent(stepIndex)}</p>
                                <div style={{minHeight: '100px'}}>
                                    {this.getStepForm(stepIndex)}
                                </div>
                                <div style={{marginTop: 15, float: 'left'}}>
                                    <RaisedButton
                                        label={stepIndex === 3 ? 'خرید' : 'مرحله بعد'}
                                        primary={true}
                                        onClick={this.handleNext}
                                    />
                                    <FlatButton
                                        label="بازگشت"
                                        disabled={stepIndex === 0}
                                        onClick={this.handlePrev}
                                        style={{marginRight: 12}}
                                    />
                                </div>
                            </div>
                        )}
                    </div>
                    <Snackbar
                        open={!cont}
                        message={error}
                        autoHideDuration={4000}
                        onRequestClose={this.handleRequestClose}
                    />
                </div>
            );
        }
}


RegisterStepper.contextTypes = {
    screenType: PropTypes.string,
    width:PropTypes.number
};
