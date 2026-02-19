import { AsyncPipe, NgFor } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { TipoUsuario } from './tipo-usuario';
import { Observable } from 'rxjs';

@Component({
  selector: 'usuario-form',
  imports: [NgFor, HttpClientModule, AsyncPipe],
  templateUrl: './usuario-form.html',
  styleUrl: './usuario-form.css',
})
export class UsuarioForm implements OnInit {

    private readonly API = 'http://localhost:8080/api/usuario';

    private readonly user = 'usuariocadusu';
    private readonly pwd  = 'passw@rd';
    private readonly encodedCredentials = btoa(`${this.user}:${this.pwd}`);

    private readonly httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Accept': "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
          'Authorization': 'Basic ' + this.encodedCredentials
        })
    };

    options: Observable<any[]> = new Observable;
    users: Observable<any[]> = new Observable;


    constructor (private http: HttpClient) {
    }

    ngOnInit(): void {
        let url = `${this.API}/tipos-usuario`;
        this.options = this.http.get<any[]>(url,this.httpOptions).pipe();
    }

    onSelectChange(value: string): void {
        let url = `${this.API}/by-tipo-usuario/${value}`;
        this.users = this.http.get<any[]>(url, this.httpOptions).pipe();
    }

}
