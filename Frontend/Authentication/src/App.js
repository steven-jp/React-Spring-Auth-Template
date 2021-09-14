import "./App.css";
import Login from "./Views/Login";
import NavBar from "./Views/NavBar";
import Home from "./Views/Home";
import NotFound from "./Views/NotFound";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Switch>
          <Route path="/" exact component={Home} />
          <Route path="/login" component={Login} />
          <Route component={NotFound} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
