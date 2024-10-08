import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, NzInputModule, NzFormModule, ReactiveFormsModule, RouterOutlet],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private authservice: AuthService,
    private message: NzMessageService,
    private router: Router ){}

  ngOnInit(){
    this.registerForm = this.fb.group({
      username: [null, [Validators.email, Validators.required]],
      password: [null, Validators.required],
      name: [null, Validators.required]
    })
  }

  submitForm(){
    this.authservice.register(this.registerForm.value).subscribe(res => {
      console.log(res)
        if (res.id != null){
          this.message.success('Sugnup successfull')
          this.router.navigateByUrl('/')
        }
        else{
          this.message.error(`${res.message}`, {nzDuration: 5000})
        }
      }, 
      (error) => {
        console.error(error);
        this.message.error('An error occurred during registration', { nzDuration: 5000 });
      }
      )
  }
}
