// import Button from "./Button";
// import styled from "./App.module.css"
import { useState, useEffect } from "react";

function App() {
  const [counter, setCounter] = useState(0);
  const [keyword, setKeyword] = useState("");
  const onClick = () => { setCounter((current) => current + 1) }
  const onChange = (event) => setKeyword(event.target.value);

  useEffect(() => {
    console.log("I'm run only once...")
  }, []);
  useEffect(() => {
    if (keyword !== "" && keyword.length > 5) {
      console.log("search for", keyword);
    } else {
      console.log("more typing");
    }
  }, [keyword]);
  useEffect(() => {
    console.log("I'm run couter change");
  }, [counter]);
  useEffect(() => {
    console.log("I run when keyword & coutner change");
  }, [keyword, counter]);

  return (
    <div className="App">
      <input value={keyword || ""} onChange={onChange} type="text" placeholder="Searc here..." />
      <h1> {counter}</h1>
      <button onClick={onClick}>Click me</button>
    </div>
  );
}

export default App;
