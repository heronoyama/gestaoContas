function getElement(element){
	return document.getElementById(element);
}

function getRandomColor(){
	return "#"+((1<<24)*Math.random()|0).toString(16);
}