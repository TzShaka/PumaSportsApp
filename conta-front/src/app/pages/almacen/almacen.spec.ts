import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Almacen } from './almacen';

describe('Almacen', () => {
  let component: Almacen;
  let fixture: ComponentFixture<Almacen>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Almacen]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Almacen);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
