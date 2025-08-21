import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <div style="text-align: center; padding: 50px;">
      <h1>{{ title }}</h1>
      <p>Welcome to your lightweight Angular app!</p>
      <button (click)="incrementCounter()">
        Clicked {{ counter }} times
      </button>
    </div>
  `,
  styles: [`
    h1 {
      color: #3f51b5;
      font-family: Arial, sans-serif;
    }
    button {
      background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 16px;
      margin-top: 20px;
    }
    button:hover {
      background-color: #45a049;
    }
  `]
})
export class AppComponent {
  title = 'Lightweight Angular App';
  counter = 0;

  incrementCounter(): void {
    this.counter++;
  }
}