import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UsuarioForm } from "./usuario-form/usuario-form";


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, UsuarioForm],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('usuarios-app');
}
