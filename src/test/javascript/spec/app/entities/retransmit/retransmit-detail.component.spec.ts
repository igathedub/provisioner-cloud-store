import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { RetransmitDetailComponent } from 'app/entities/retransmit/retransmit-detail.component';
import { Retransmit } from 'app/shared/model/retransmit.model';

describe('Component Tests', () => {
  describe('Retransmit Management Detail Component', () => {
    let comp: RetransmitDetailComponent;
    let fixture: ComponentFixture<RetransmitDetailComponent>;
    const route = ({ data: of({ retransmit: new Retransmit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [RetransmitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RetransmitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RetransmitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.retransmit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
