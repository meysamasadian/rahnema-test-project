import React, {Component} from 'react'
import ReactDOM from 'react-dom'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import { Provider } from 'react-redux'
import { createStore } from 'redux'
import ClientApp from './redux/reducers'
import ClientApplication from "./components/ClientApplication";

const muiTheme = getMuiTheme({
    fontFamily: 'IRANSansWeb',
    isRtl: true
});

const store = createStore(ClientApp);

class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <Provider store={store}>
                    <MuiThemeProvider muiTheme={muiTheme}>
                        <ClientApplication/>
                    </MuiThemeProvider>
                </Provider>
            </div>
        )
    }
}

const root = document.querySelector('#app')
ReactDOM.render(<App/>, root)
