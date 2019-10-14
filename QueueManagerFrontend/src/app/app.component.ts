import {Component, OnInit} from '@angular/core';
import {Aircraft, SystemStatus} from '../../typings/SystemStatus';
import {HttpService} from './service/http.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  status: SystemStatus;

  constructor(private httpservice: HttpService) {

  }

  ngOnInit(): void {
    this.getStatus();
  }

  enqueue(aircraft: Aircraft) {
    console.log(aircraft);
    this.httpservice.post('./aqmRequestProcess/enqueue', aircraft).subscribe(data => {
      this.getStatus();
    });
  }

  getStatus() {
    this.httpservice.get('./aqmRequestProcess/status').subscribe((data: SystemStatus) => {
      this.status = data;
    });
  }

  dequeue() {
    this.httpservice.post('./aqmRequestProcess/dequeue', '').subscribe(data => {
      this.getStatus();
    }, error => {
      console.log(error);
      this.getStatus();
    });
  }
}
