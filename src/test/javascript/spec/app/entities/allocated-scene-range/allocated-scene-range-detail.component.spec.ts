import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedSceneRangeDetailComponent } from 'app/entities/allocated-scene-range/allocated-scene-range-detail.component';
import { AllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';

describe('Component Tests', () => {
  describe('AllocatedSceneRange Management Detail Component', () => {
    let comp: AllocatedSceneRangeDetailComponent;
    let fixture: ComponentFixture<AllocatedSceneRangeDetailComponent>;
    const route = ({ data: of({ allocatedSceneRange: new AllocatedSceneRange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedSceneRangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllocatedSceneRangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedSceneRangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allocatedSceneRange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
