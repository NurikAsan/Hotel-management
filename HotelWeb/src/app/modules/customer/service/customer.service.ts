import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../../../auth/services/storage/user-storage.service';

const BASIC_URL = 'http://localhost:8080/'

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getAvailableRoom(pageNumber: number): Observable<any>{
    return this.http.get(BASIC_URL + `api/customer/rooms/${pageNumber}`, {      
      headers: this.createAuthorizationHeader(),
    })
  }

  bookRoom(dto: any): Observable<any>{
    return this.http.post(BASIC_URL + `api/customer/book`, dto, {      
      headers: this.createAuthorizationHeader(),
    })
  }

  getBookingsById(pageNumber: number): Observable<any>{
    const id = UserStorageService.getUserId();
    return this.http.get(BASIC_URL + `api/customer/booking/${id}/${pageNumber}`, {      
      headers: this.createAuthorizationHeader(),
    })
  }

  getRefPay(name: string, amount: number): Observable<any>{
    return this.http.get(BASIC_URL + `api/v1/payment/checkout?name=${name}&amount=${amount}`)
  }


  createAuthorizationHeader(){
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set(
      'Authorization',
      'Bearer ' + UserStorageService.getToken()
    )
  }

}
