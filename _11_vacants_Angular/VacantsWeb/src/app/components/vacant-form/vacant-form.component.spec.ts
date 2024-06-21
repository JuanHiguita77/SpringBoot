import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacantFormComponent } from './vacant-form.component';

describe('VacantFormComponent', () => {
  let component: VacantFormComponent;
  let fixture: ComponentFixture<VacantFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VacantFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VacantFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
