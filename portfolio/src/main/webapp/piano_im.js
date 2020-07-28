const notes = new Map();
notes.set('C', 'white');
notes.set('C#', 'black');
notes.set('D', 'white');
notes.set('D#', 'black');
notes.set('E', 'white');
notes.set('F', 'white');
notes.set('F#', 'black');
notes.set('G', 'white');
notes.set('G#', 'black');
notes.set('A', 'white');
notes.set('A#', 'black');
notes.set('B', 'white');
notes.set('C2', 'white');
notes.set('C#2', 'black');
notes.set('D2', 'white');
notes.set('D#2', 'black');
notes.set('E2', 'white');

const svgns = 'http://www.w3.org/2000/svg';
const svg = document.getElementsByTagName('svg')[0];
const whiteWidth = 100;
const blackWidth = 30;
const whiteHeight = '300';
const blackHeight = '200';
let x = 0;
y = '0';
for (const name of notes.keys()) {
  const rect = document.createElementNS(svgns, 'rect');
  rect.setAttributeNS(null, 'x', x);
  rect.setAttributeNS(null, 'y', y);
  rect.setAttributeNS(null, 'data-key', name);
  if (notes.get(name) == 'white') {
    rect.setAttributeNS(null, 'height', whiteHeight);
    rect.setAttributeNS(null, 'width', whiteWidth);
    rect.setAttributeNS(null, 'fill', 'white');
    rect.setAttributeNS(null, 'class', 'key');
    rect.setAttributeNS(null, 'stroke', 'black');
    rect.setAttributeNS(null, 'stroke-width', '5');
    x += whiteWidth;
  } else {
    rect.setAttributeNS(null, 'height', blackHeight);
    rect.setAttributeNS(null, 'width', blackWidth);
    rect.setAttributeNS(null, 'fill', 'black');
    rect.setAttributeNS(null, 'class', 'key');
    x += blackWidth;
  }
  svg.appendChild(rect);
}
