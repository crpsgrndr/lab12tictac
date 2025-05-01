import React from 'react';
import './App.css';
import { GameState, Cell } from './game';
import BoardCell from './Cell';

interface Props {}

class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;
  

  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [],
      currentPlayer: "",
      winner: "",
      draw: false
    };
  }  

  newGame = async () => {
    const response = await fetch('/api/newgame');
    const json = await response.json();
    this.setState({
      cells: json['cells'],
      currentPlayer: json['currentPlayer'],
      winner: json['winner'],
      draw: json['draw']
    });
  };
  

  play(x: number, y: number): React.MouseEventHandler {
    return async (e) => {
      e.preventDefault();
      const response = await fetch(`/api/play?x=${x}&y=${y}`);
      const json = await response.json();
      this.setState({
        cells: json['cells'],
        currentPlayer: json['currentPlayer'],
        winner: json['winner'],
        draw: json['draw']
      });
    };
  }
  
  createCell(cell: Cell, index: number): React.ReactNode {
    const className = 'cell';
  
    if (cell.playable) {
      return (
        <div
          key={index}
          className={className}
          onClick={this.play(cell.x, cell.y)}
        >
          {cell.text}
        </div>
      );
    } else {
      return (
        <div key={index} className={className}>
          {cell.text}
        </div>
      );
    }
  }

  componentDidMount(): void {
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
  }
  undo = async () => {
    if (this.state.winner || this.state.draw) {
      return;
    }
    const response = await fetch('/api/undo');
    const json = await response.json();
    this.setState({
      cells: json['cells'],
      currentPlayer: json['currentPlayer'],
      winner: json['winner'],
      draw: json['draw']
    });
  };
  
  
  render(): React.ReactNode {
    return (
      <div>
        <div id="instructions">
  {this.state.winner
    ? <h3>Player {this.state.winner} wins!</h3>
    : this.state.draw
      ? <h3>Draw!</h3>
      : <h3>Current turn: Player {this.state.currentPlayer}</h3>}
</div>


        <div id="board">
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>

        <div id="bottombar">
          <button onClick={this.newGame}>New Game</button>
          <button onClick={this.undo}>Undo</button>
        </div>
      </div>
    );
  }
}

export default App;
