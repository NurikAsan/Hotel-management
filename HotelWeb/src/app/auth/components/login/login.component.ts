import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule  , Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthService } from '../../services/auth/auth.service';
import { UserStorageService } from '../../services/storage/user-storage.service';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, NzInputModule, NzFormModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm!: FormGroup;

  constructor(private fb: FormBuilder, 
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router){}

  ngOnInit(){
    this.loginForm = this.fb.group({
      email: [null, [Validators.email, Validators.required]],
      password: [null, Validators.required],
    })
  }

  submitForm() {
    this.authService.login(this.loginForm.value)
    .subscribe(res => {
      if (res.userId != null){
        const user = {
          id: res.userId,
          role: res.userRole
        }
        this.message.success('Sugnup successfull')
        this.router.navigateByUrl('/')
        UserStorageService.saveUser(user);
        UserStorageService.saveToken(res.jwt);

        if (UserStorageService.isAdminLoggedIn()){
          this.router.navigateByUrl('/admin/dashboard')
        }
        else if(UserStorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl('/customer/rooms')
        }
      }
    }, error => {
    this.message.error(
      'Bad credentials',
      { nzDuration: 5000}
    )})
  }
  
}
