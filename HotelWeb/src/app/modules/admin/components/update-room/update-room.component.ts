import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';
import { CommonModule } from '@angular/common';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';


@Component({
  selector: 'app-update-room',
  standalone: true,
  imports: [CommonModule, NzFormModule, NzInputModule, ReactiveFormsModule],
  templateUrl: './update-room.component.html',
  styleUrl: './update-room.component.css'
})
export class UpdateRoomComponent {

  updatedRoomForm: FormGroup;
  id = this.activedroute.snapshot.params['id'];

  constructor(private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private adminService: AdminService,
    private activedroute: ActivatedRoute){
      this.updatedRoomForm = this.fb.group({
        name: ['', Validators.required],
        type: ['', Validators.required],
        price: ['', Validators.required],
      })
      
      this.getRoomByI();
    }

    submitForm(){
      this.adminService.updateRoomDetails(this.id, this.updatedRoomForm.value).subscribe(res => {
        this.message.success(
          'Room updated succesfully',
          { nzDuration: 500 }
        );
        this.router.navigateByUrl('/admin/dashboard')
      }, err => {
        this.message.error(`${err.error}`, {nzDuration: 5000})
      })
    }

    getRoomByI(){
      this.adminService.getRoomById(this.id).subscribe(res => {
        this.updatedRoomForm.patchValue(res)
      }, error => {
        this.message.error(`${error.error}`, {nzDuration: 5000})
      }
      )
    }
}