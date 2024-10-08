import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-post-room',
  standalone: true,
  imports: [CommonModule, NzInputModule, NzFormModule, ReactiveFormsModule],
  templateUrl: './post-room.component.html',
  styleUrl: './post-room.component.css'
})
export class PostRoomComponent {
  roomDetailsForm: FormGroup

  constructor(private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private adminService: AdminService){
      this.roomDetailsForm = this.fb.group({
        name: ['', Validators.required],
        type: ['', Validators.required],
        price: ['', Validators.required]
      })
    }

    submitForm(){
      console.log(this.roomDetailsForm.value)
      this.adminService.postRoomDetails(this.roomDetailsForm.value).subscribe(res => {
        this.message.success(
          'Room Posted Succesfully',
          {nzDuration: 500}
        );
        this.router.navigateByUrl('/admin/dashboard')
      }, err => {
        this.message.error(
          `${err.error}`,
          {nzDuration: 5000}
        )
      }
      )
    }
}
