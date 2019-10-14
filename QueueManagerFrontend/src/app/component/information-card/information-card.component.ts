import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Aircraft} from '../../../../typings/SystemStatus';

@Component({
  selector: 'app-information-card',
  templateUrl: './information-card.component.html',
  styleUrls: ['./information-card.component.css']
})
export class InformationCardComponent implements OnInit {

  @Input() listSize;
  @Input() type;
  @Input() capacity;

  @Output() enqueueEvt = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  enqueue() {
    const ac: Aircraft = {aircraftType: '', aircraftSize: '', timeAdded: undefined};
    ac.aircraftSize = this.capacity;
    ac.aircraftType = this.type;
    this.enqueueEvt.emit(ac);
  }
}
