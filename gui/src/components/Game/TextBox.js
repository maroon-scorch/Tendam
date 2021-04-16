// Function for a TextBox
function TextBox(props) {
  const a = (event) => {
    props.change(event.target.value);
  }
  return (
    <div className="TextBox">
    <label>
      {props.label}:
      <input type={'text'} onChange={a}>
      </input>
    </label>
    </div>
  );
}


export default TextBox;
