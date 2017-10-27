import React, {Component} from 'react'
import Master from "../components/master/Master";

export default class ClientApplication extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        console.log("render: ClientApplication");
        return (
            <div>
                <Master />
            </div>
        )
    }
}

