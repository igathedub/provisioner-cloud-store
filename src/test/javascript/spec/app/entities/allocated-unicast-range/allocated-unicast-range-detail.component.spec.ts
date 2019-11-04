import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedUnicastRangeDetailComponent } from 'app/entities/allocated-unicast-range/allocated-unicast-range-detail.component';
import { AllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';

describe('Component Tests', () => {
  describe('AllocatedUnicastRange Management Detail Component', () => {
    let comp: AllocatedUnicastRangeDetailComponent;
    let fixture: ComponentFixture<AllocatedUnicastRangeDetailComponent>;
    const route = ({ data: of({ allocatedUnicastRange: new AllocatedUnicastRange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedUnicastRangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllocatedUnicastRangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedUnicastRangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allocatedUnicastRange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
